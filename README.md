📁 Java Directory Watcher with Email Notifications
Automated file system monitoring tool that watches a directory for changes, sends email alerts with attachments, and performs cleanup operations. Built with Java NIO and JavaMail API.

🚀 Features
Real-time monitoring of directory for file creations

Email notifications with file attachments

Automatic cleanup of processed files

Configurable via environment variables

Robust error handling for file operations and email delivery

🔧 How It Works
Monitors specified directory for new files (CREATE events)

When detected:

Sends email notification with the file attached

Waits 5 seconds (configurable)

Automatically deletes the processed file

Logs all operations to console

⚙️ Setup Instructions
Prerequisites
Java 8+

Maven

Gmail account (or other SMTP provider)
