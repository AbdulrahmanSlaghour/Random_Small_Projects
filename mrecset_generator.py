import tkinter as tk
from tkinter import messagebox
from datetime import datetime
import os

def get_desktop_path():
    # Try standard Desktop first
    desktop = os.path.join(os.path.expanduser("~"), "Desktop")
    if os.path.exists(desktop):
        return desktop
    # If not, try OneDrive Desktop (common on Windows 10/11)
    onedrive = os.environ.get("OneDrive")
    if onedrive:
        desktop_onedrive = os.path.join(onedrive, "Desktop")
        if os.path.exists(desktop_onedrive):
            return desktop_onedrive
    # Fallback to current directory
    return "."

def generate_file():
    try:
        now = datetime.now()
        time_line = f"TIME:{now.strftime('%H:%M %Y/%m/%d')}"
        bit_line = "BIT:4    //Bit Rate Setting(1~4), 1 is 32K, 2 is 64K, 3 is 128K, 4 is 192K. Advice 4"
        part_line = "PART:60    //Recording File Segmented Time(10--240 minutes). Advice 60"
        content = f"{time_line}\n{bit_line}\n{part_line}"

        desktop_path = get_desktop_path()
        file_path = os.path.join(desktop_path, "MRECSET.TXT")

        with open(file_path, "w", encoding="utf-8") as f:
            f.write(content)

        messagebox.showinfo("Success", f"MRECSET.TXT created on your Desktop!")
    except Exception as e:
        messagebox.showerror("Error", f"Something went wrong:\n{e}")

# GUI
root = tk.Tk()
root.title("MRECSET Generator")
root.geometry("320x160")
root.resizable(False, False)

label = tk.Label(root, text="Generate MRECSET.TXT for your recorder", font=("Arial", 12), pady=10)
label.pack()

generate_button = tk.Button(root, text="Generate File", font=("Arial", 11), command=generate_file)
generate_button.pack(pady=10)

root.mainloop()
