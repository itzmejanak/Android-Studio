from kivy.uix.boxlayout import BoxLayout
from kivy.uix.filechooser import FileChooserIconView
from kivy.uix.button import Button


class FileSelector(BoxLayout):
    def __init__(self, on_file_selected, **kwargs):
        super().__init__(orientation="vertical", **kwargs)
        self.on_file_selected = on_file_selected
        self.file_chooser = FileChooserIconView(filters=["*.apk"])
        self.select_btn = Button(text="Select", size_hint=(1, 0.2))
        self.select_btn.bind(on_press=self.select_file)

        self.add_widget(self.file_chooser)
        self.add_widget(self.select_btn)

    def select_file(self, instance):
        selected = self.file_chooser.selection
        if selected:
            self.on_file_selected(selected[0])
