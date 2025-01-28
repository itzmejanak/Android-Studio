import os
import subprocess
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.label import Label
from kivy.uix.filechooser import FileChooserIconView
from kivy.uix.popup import Popup
from permission_manager import PermissionManager
from file_selector import FileSelector
from extractor import Extractor


class MainApp(App):
    def build(self):
        self.selected_apk_path = None
        self.extracted_dir = "/sdcard/BRadare2/lib/arm64-v8a"  # Hardcoded extracted directory
        self.output_dir = "/sdcard/BRadare2/Output"  # Output directory for blutter.py

        layout = BoxLayout(orientation="vertical", spacing=10, padding=10)
        self.status_label = Label(text="Select an APK file to extract .so files", size_hint=(1, 0.2))
        self.select_btn = Button(text="Select APK", size_hint=(1, 0.2))
        self.extract_btn = Button(text="Extract .so Files", size_hint=(1, 0.2))

        self.select_btn.bind(on_press=self.select_apk)
        self.extract_btn.bind(on_press=self.extract_files)

        layout.add_widget(self.status_label)
        layout.add_widget(self.select_btn)
        layout.add_widget(self.extract_btn)

        return layout

    def select_apk(self, instance):
        if not PermissionManager.check_permissions():
            PermissionManager.request_permissions()
            return

        popup_content = FileSelector(on_file_selected=self.set_selected_apk)
        popup = Popup(title="Select APK", content=popup_content, size_hint=(0.9, 0.9))
        popup.open()

    def set_selected_apk(self, apk_path):
        self.selected_apk_path = apk_path
        self.status_label.text = f"APK selected: {apk_path.split('/')[-1]}"

    def extract_files(self, instance):
        if not self.selected_apk_path:
            self.status_label.text = "No APK selected!"
            return

        if not PermissionManager.check_permissions():
            self.status_label.text = "Storage permissions required!"
            return

        message = Extractor.extract_so_files(self.selected_apk_path, self.extracted_dir)
        self.status_label.text = message

        if "Extraction complete!" in message:
            self.show_decompile_popup()

    def show_decompile_popup(self):
        layout = BoxLayout(orientation="vertical", spacing=10, padding=10)
        label = Label(text="Do you want to decompile the extracted .so files?")
        yes_btn = Button(text="Yes", size_hint=(1, 0.2))
        no_btn = Button(text="No", size_hint=(1, 0.2))

        layout.add_widget(label)
        layout.add_widget(yes_btn)
        layout.add_widget(no_btn)

        popup = Popup(title="Decompile Files", content=layout, size_hint=(0.8, 0.4))

        yes_btn.bind(on_press=lambda instance: self.run_blutter(popup))
        no_btn.bind(on_press=lambda instance: popup.dismiss())

        popup.open()

    def run_blutter(self, popup):
        popup.dismiss()

        # Ensure the output directory exists
        if not os.path.exists(self.output_dir):
            os.makedirs(self.output_dir)

        try:
            # Run blutter.py using subprocess
            result = subprocess.run(
                ["python", "blutter-termux/blutter.py", self.extracted_dir, self.output_dir],
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                text=True,
            )
            output = result.stdout
            error = result.stderr

            # Print the output and error for debugging
            print("BLUTTER Output:")
            print(output)  # This will give you the standard output from blutter.py

            print("BLUTTER Error:")
            print(error)  # This will give you any error messages from blutter.py

            if result.returncode == 0:
                self.status_label.text = f"Decompilation complete! Output saved to: {self.output_dir}"
            else:
                self.status_label.text = f"Error during decompilation: {error}"

        except Exception as e:
            self.status_label.text = f"Error running blutter.py: {e}"
            print(f"Exception during blutter.py execution: {e}")


if __name__ == "__main__":
    MainApp().run()
