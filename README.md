# operating-system(Interpreter)

## Table of content
- [Project Description](#project-description)
- [First phase](#First-phase)
- [Second phase](#Second-phase)



## Project Description

### Course 
Operating Systems (CSEN 602), spring 2022

### Theme
The theme of the project, is to understand the concepts of the Operating System  by building
a processor and then experiment it to see how the OS manages
resources and processes.

### Overview 
This project is divided to section , First section is to build an interpreter to translate a text file and call the scheduler to schedule the programs in the text file to the  >>>> CPU .
second section to implement the memory that stores processes' data .

### Objectives
- understand the concept of OS
- Learn the process of the simulation of the processor
- Learn how to work together as a team on GitHub.

## First phase
In the first phase of this project we simulate the OS by building the main component of The CPU :
- [Scheduler](##Scheduler)
- [Ready Queue](#First-section)
- [Blocked Queue](#First-section)
- [Mutex](#First-section)
- [System Calls](#First-section)
- [Code Parser](#Code_Parser)
 
### Scheduler 
we implemented the scheduler with the round robin algorithm where we assign each process a fixed quantum to run and when it finishs its quantum it return back to the ready queue waiting for its time slice and keep rounding untill all the process are finished .
    you can read more about round robin algorithm from here : https://www.techtarget.com/whatis/definition/round-robin#:~:text=A%20round%2Drobin%20story%20is,ended%20depend%20on%20the%20rules.
### Ready Queue
### Blocked Queue 
### Mutex
### System Calls
### Code Parser
## Second phase
In the first phase of this project we implemented the Memory of the OS and the Swaping algorithm :
- [Memory](##Scheduler)
- [Swaping Algorithm](#First-section)
- [Disk](#First-section)
- [Process](#First-section)
- [PCB](#First-section)
- [Process state](#Process-state)
