import os
import zipfile


class Extractor:
    @staticmethod
    def extract_so_files(apk_path, output_dir):
        if not os.path.exists(apk_path):
            return "APK file does not exist."

        if not os.path.exists(output_dir):
            os.makedirs(output_dir)

        try:
            with zipfile.ZipFile(apk_path, 'r') as apk:
                extracted_count = 0
                for entry in apk.namelist():
                    if entry.startswith("lib/") and (entry.endswith("libapp.so") or entry.endswith("libflutter.so")):
                        apk.extract(entry, output_dir)
                        extracted_count += 1

                if extracted_count > 0:
                    return f"Extraction complete! {extracted_count} files saved to: {output_dir}"
                else:
                    return "No matching .so files found in the APK."

        except Exception as e:
            return f"Error during extraction: {e}"
