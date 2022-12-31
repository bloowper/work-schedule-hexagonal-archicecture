# Sample hexagonal archicecture
This project is field for me to learning and experiment with hexagonal approach

# Domain
The purpose of the domain is to develop a work schedule, which outlines
the energy consumption plan, based on set of a policies assigned to the device.
Each work shift within the schedule will be determined by these policies 

# Packages explanation
* domain - contain domain
* ports- contain ports to domain \n
  * The 'ports' package contains two subpackages, 'input' and 'output', which represent the inputs to the domain and the outputs from the domain, respectively.
* infrastructure - contain implementation of ports, also known as adapters
  * here also will be stored any configuration etc. for domain and passed by some kind of port to domain

# Test explanation
* 'UT' - unit test
* 'BT' - behavioral test, mean that there is behaviour of entire domain under test.
* 'IT' - integration test


