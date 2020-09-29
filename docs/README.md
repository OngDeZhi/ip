# User Guide

Duke is a Personal Assistant Chat Bot that helps a person to keep track of various things, 
such as a Todo, Deadline, or Event.

## Table of Contents
* [1. Quick Start](#1-quick-start)
* [2. Features](#2-features)
    * [2.1 Task Management](#21-task-management)
    * [2.2 Task Searching](#22-task-searching)
    * [2.3 Persistent Data](#23-persistent-data)
* [3. Usage](#3-usage)
    * [3.1 Add a Todo task: `todo`](#31-add-a-todo-task-todo)
    * [3.2 Add a Deadline task: `deadline`](#32-add-a-deadline-task-deadline)
    * [3.3 Add an Event task: `event`](#33-add-an-event-task-event)
    * [3.4 Mark a task as done: `done`](#34-mark-a-task-as-done-done)
    * [3.5 Delete a task: `delete`](#35-delete-a-task-delete)
    * [3.6 List tasks that are due: `due`](#36-list-tasks-that-are-due-due)
    * [3.7 Find task by the keyword: `find`](#37-find-task-by-the-keyword-find)
    * [3.8 List all tasks: `list`](#38-list-all-tasks-list)
    * [3.9 Exit the program: `bye`](#39-exit-the-program-bye)
* [4. FAQ](#4-faq)
* [5. Command Summary](#5-command-summary)

## 1. Quick Start
1. Ensure you have JDK 11 installed.
2. Download the latest `ip.jar`.
3. Place the jar file in a directory that you are comfortable with.
4. Run Duke in a command window using the command: `java -jar ip.jar`.
5. Refer to [Usage](#3-usage) or [Command Summary](#5-command-summary) for details on using Duke.

## 2. Features
#### 2.1 Task Management
User can: 
* Add a task (Todo, Deadline, or Event) to the list.
* Remove a task from the list.
* Mark a task as done in the list.
* List all tasks in the list.

#### 2.2 Task Searching
User can search for their desired tasks using a keyword or due date.

#### 2.3 Persistent Data
All changes made on the task list by the user will be saved to a local storage file automatically, and 
when Duke launches, the tasks saved in the local storage file will be loaded automatically.

## 3. Usage
#### 3.1 Add a Todo task: `todo`

This will add a Todo task to the task list.

Format: `todo <TASK_DESCRIPTION>`

Example of usage: `todo read book`

Expected outcome: 
```
Gotcha! I've added this task: 
	[T][✗] read book
There's currently 1 task(s) in the list.
```

<br/>

#### 3.2 Add a Deadline task: `deadline`

This will add a Deadline task to the task list.

Format: `deadline <TASK_DESCRIPTION> /by <YYYY-MM-DD HH:MM>`

Example of usage: `deadline return book /by 2020-06-06 12:30` 

Expected outcome: 
```
Gotcha! I've added this task: 
	[D][✗] return book (by: Jun 06 2020 12:30)
There's currently 2 task(s) in the list.
```

<br/>

#### 3.3 Add an Event task: `event`

This will add an Event task to the task list.

Format: `event <TASK_DESCRIPTION> /at <YYYY-MM-DD HH:MM>`

Example of usage: `event project meeting /at 2020-08-05 13:30`

Expected outcome: 
```
Gotcha! I've added this task: 
	[E][✗] project meeting (at: Aug 05 2020 13:30)
There's currently 3 task(s) in the list.
```

<br/>

#### 3.4 Mark a task as done: `done`

This will mark a task as done in the task list.

Format: `done <TASK_INDEX>`

Example of usage: `done 1`

Expected outcome: 
```
Awesome!! Just 2 more task(s) to go!
	[T][✓] read book
```

<br/>

#### 3.5 Delete a task: `delete`

This will delete a task from the task list.

Format: `delete <TASK_INDEX>`

Example of usage: `delete 1`

Expected outcome: 
```
Noted! I have removed this task: 
 	[T][✓] read book
There's currently 2 task(s) in the list.
```

<br/>

#### 3.6 List tasks that are due: `due`

This will list all tasks that are due on a specific day.

Format: `due <YYYY-MM-DD>`

Example of usage: `due 2020-08-05`

Expected outcome: 
```
Here is a list of tasks due for that day: 
 	1. [E][✗] project meeting (at: Aug 05 2020 13:30)
 	2. [E][✗] CS2113T meeting (at: Aug 05 2020 15:45)
```

<br/>

#### 3.7 Find task by the keyword: `find`

This will find and list all tasks whose description contains the specified keyword.

Format: `find <KEYWORD>`

Example of usage: `find meeting`

Expected outcome: 
```
Here are the matching tasks in your list: 
 	1. [E][✗] project meeting (at: Aug 05 2020 13:30)
 	2. [E][✗] CS2113T meeting (at: Aug 05 2020 15:45)
 	3. [D][✗] meeting minutes (by: Aug 13 2020 23:59)
```

<br/>

#### 3.8 List all tasks: `list`

This will list all tasks that the user has added to the list.

Format: `list`

Example of usage: `list`

Expected outcome: 
```
Here are the tasks in your list: 
 	1. [D][✗] return book (by: Jun 06 2020 12:30)
 	2. [E][✗] project meeting (at: Aug 05 2020 13:30)
 	3. [E][✗] CS2113T meeting (at: Aug 05 2020 15:45)
 	4. [D][✗] meeting minutes (by: Aug 13 2020 23:59)
```

<br/>

#### 3.9 Exit the program: `bye`

This will exit the program.

Format: `bye`

Example of usage: `bye`

Expected outcome: 
```
Bye-bye. Hope to see you again soon!
```

## 4. FAQ
Q: Where are my tasks stored?

A: They can be found in the current directory where you run Duke, at `data/duke.txt`.

Q: Why does it say "Duke could not load some of the saved task(s)."?

A: It is likely that you have an entry that cannot be processed by Duke, 
e.g. missing task information or incorrect task entry format in the file.

## 5. Command Summary

|Feature                               |Command                                               |
|---                                   |---                                                   |
| Add a Todo task                      |`todo <TASK_DESCRIPTION>`                             |
| Add a Deadline task                  |`deadline <TASK_DESCRIPTION> /by <YYYY-MM-DD HH:MM>`  |
| Add an Event task                    |`event <TASK_DESCRIPTION> /at <YYYY-MM-DD HH:MM>`     |
| Mark a task as done                  |`done <TASK_INDEX>`                                   |
| Delete a task                        |`delete <TASK_INDEX>`                                 |
| Find a task by keyword               |`find <KEYWORD>`                                      |
| Find task(s) due on a specific date  |`due <YYYY-MM-DD>`                                    |
| List all tasks                       |`list`                                                |
| Exit                                 |`bye`                                                 |