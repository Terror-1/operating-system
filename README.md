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
- [Ready Queue](#Ready-Queue)
- [Blocked Queue](#Blocked-Queue)
- [Mutex](#Mutex)
- [System Calls](#System-Calls)
- [Code Parser](#Code-Parser)
 
### Scheduler 
we implemented the scheduler with the round robin algorithm where we assign each process a fixed quantum to run and when it finishs its quantum it return back to the ready queue waiting for its time slice and keep rounding untill all the process are finished .
    you can read more about round robin algorithm from here : https://www.techtarget.com/whatis/definition/round-robin#:~:text=A%20round%2Drobin%20story%20is,ended%20depend%20on%20the%20rules.    
### Ready Queue
The ready Queue is a pool of process that are waiting to take their quantum.
### Blocked Queue
The blocked queue conatins a pool of process that are waiting for an event to occur.
### Mutex
We implemnted three types of mutex (Mutex over the input , Mutex over the output , Mutex over the File):
when a process need to use one of thoose resources it calls a method ``` SemWait ``` if the resource is available it will take it else it will be bloced over this resource untill the process holding this resource signal it with a the method ``` SemSignal ```
### System Calls
In the component of the processor we created the methods that access pieces of Hardware Like ``` readFile ``` ``` writeFile ``` ``` print ```
### Code Parser
The job of code parser is from it is name  just parse the textfile (unparsed) into a format of parsed line of code that we can execute.
## Second phase
In the first phase of this project we implemented the Memory of the OS and the Swaping algorithm :
- [Memory](##Memory)
- [Swaping Algorithm](#Swaping-Algorithm)
- [Disk](#Disk)
- [PCB](#PCB)
- [Process state](#Process-state)
### Memory 
Our memory is this project is a generic one holds both instruction and data in the Von_neuman architecture .
Our memory can hold up to 40 words . we use the Fixed-partioning method where the memory can hold 2 processes each of them is a fixed size of 20 meaning 
the First segmant of the memory contains 20 words distributed among process's component 
### Swaping Algorithm 
For the simplicity , we know that the memory holds 2 process only , so definitely on of them is running in the CPU and the other is either ready or blocked , so when a new process arrives and the memory is full we swap the non-running one to Disk and put the one which arrives it it place .
### Disk 
We simulate the Disk with a text file that contains unparsed line of code .
## PCB 
The pcb consists of four main elements : 
- process Id
- program Counter
- process state
- Memory boundries (start , end)
## Process state 
we have 4 main process states >> ```Ready``` ```BLOCKED``` ```RUNNING``` ```FINISHED``` . 
and we implemented that using an enum .
