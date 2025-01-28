import os


class PermissionManager:
    @staticmethod
    def check_permissions():
        if os.name == 'posix':
            return True  # Assume permissions are granted in Linux-based systems
        else:
            # For Windows or other platforms, you can add logic here.
            return True

    @staticmethod
    def request_permissions():
        print("Please manually enable storage permissions if required.")
