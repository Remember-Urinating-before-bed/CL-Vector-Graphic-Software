# Command Line Vector Graphic Software

Describe here all the commands that the system supports. For each command, use screenshots 
to show the results under different inputs. 

**General commands:**

After running the clevis, the user should be able to see the command prompt with a line 
“Please enter the command here: ” as shown in the below. 

Users can freely input commands. And if there are any invalid inputs, the command prompt will show a line “Invalid input, please enter again.”, so that the user can enter the correct command again and the program can receive the correct attributes. 
<img width="358" alt="image" src="https://user-images.githubusercontent.com/78738614/169062474-7051185b-2386-4821-9159-93677f61bc33.png">
 
 
**Creating shapes:**

In the command prompt, users can create squares, rectangles, circles, lines and groups for grouping. 

Rectangle:
For creating a rectangle, user can enter the command “rectangle n 1 1 3 2” to the command prompt to create a rectangle n, with top left position (1, 1), width 3 and height 2. The example output is shown below.
<img width="327" alt="image" src="https://user-images.githubusercontent.com/78738614/169062596-f5e6653a-1eb6-4822-846b-344958635a2d.png">

        
Line:
For line, the command “line l 1 1 3 3” will create a line name l with 2 end points (1, 1) and (3, 3). The example output is shown below.
<img width="341" alt="image" src="https://user-images.githubusercontent.com/78738614/169062728-0a9c1976-6a74-4162-989c-3fe1e0980be9.png">
           
  
Circle:
For circle, the command “circle c 3 3 5” would create a circle named c with 
centre (3, 3) and radius 5. The example output is shown below.
<img width="330" alt="image" src="https://user-images.githubusercontent.com/78738614/169062802-5b82d057-e522-491f-b8ab-ca931fe97ea2.png">

           
Square:
For creating a square, the command “square n 3 2 1” would create a square named n with coordinates (3, 2) as top left position and side length as 1. The example output is shown below.
<img width="334" alt="image" src="https://user-images.githubusercontent.com/78738614/169062852-c8d8cbbe-b24b-49b9-a8eb-cd585988a2f5.png">
               

Group:
To group, “group m n l ...” command can be used. In which a group shape m is created and the members consists of shape n, shape l and so on. The example output is shown below.
<img width="327" alt="image" src="https://user-images.githubusercontent.com/78738614/169063217-254e251a-2a2a-4fe3-83ca-6984741d639e.png">


Ungroup:
To ungroup, “ungroup m” command can be used. A group shape m is ungrouped and the members can be directly used later. The example output is shown below.
<img width="245" alt="image" src="https://user-images.githubusercontent.com/78738614/169063341-36beec2d-6b6c-4b4f-851c-209f13e20d80.png">
              

Delete: 
To delete, “delete n” command can be used. A shape n is deleted and if n is a group shape, the members are also deleted. The example output is shown below.
<img width="307" alt="image" src="https://user-images.githubusercontent.com/78738614/169063376-f5747e81-79a5-47de-b44e-1c5e2a4e4b71.png">
             

Bounding box:
To create a bounding box, “boundingbox m” command can be used. A minimal bounding box covering shape m is created. The example output is shown below.
<img width="329" alt="image" src="https://user-images.githubusercontent.com/78738614/169063443-f55eb45f-cf90-4c5b-af38-c03a6a8de2ca.png">
            

Move: (added +“.\n” in switch move)
To move, “move n 1 1” command can be used. The command will move shape n by dx 1, dy 1. The example output is shown below.
<img width="345" alt="image" src="https://user-images.githubusercontent.com/78738614/169063474-2ab5779c-817c-480b-a6e5-b6c1de7e7c3b.png">
     

Pick and move:  
To pick and move, “pick-and-move 2.99 1.01 1 1” command can be used. The command will move shape near (2.99, 1.01) by dx 1, dy 1. The example output is shown below. 
<img width="348" alt="image" src="https://user-images.githubusercontent.com/78738614/169063523-31af14d0-4e82-43a6-8ed4-c081fbd9a277.png">
      

Intersect: 
To find if there is intersection of 2 shapes, “intersect c d” command can be used. The command checks if shape c intersects with shape d and returns a Boolean to determine.
The example output is shown below.            
<img width="356" alt="image" src="https://user-images.githubusercontent.com/78738614/169063575-5e649749-5430-48fa-8b20-cf4b59100647.png">
     

List:
To list the basic information of a shape, “list n” can be used. The command will list the basic information of the shape n and its member if it is a member shape. The example output is shown below.
<img width="327" alt="image" src="https://user-images.githubusercontent.com/78738614/169063611-dfe05877-9788-44dc-8111-92093cda22a9.png">
        

List all:
To list the basic information of all the existing shapes, “listAll” can be used. The command will list the basic information of all shapes in the clevis. The example output is shown below.
<img width="354" alt="image" src="https://user-images.githubusercontent.com/78738614/169063672-e45a54ea-90b7-44b4-b9b3-475a93bb33cb.png">
    

Quit:
To quit and terminate the function, “quit” can be used. The command will exit the program and user can close the window. The example output is shown below.
 <img width="359" alt="image" src="https://user-images.githubusercontent.com/78738614/169063717-708218b4-8222-46b2-898c-54027f8f159c.png">
       

Undo-redo:
The undo-redo function can be used in all command in the above except for bundingbox, intersect, list, listAll and quit commands. The user can enter “undo” / “redo” to delete or recover the command entered. The example outputs are shown below.

<img width="218" alt="image" src="https://user-images.githubusercontent.com/78738614/169063838-a2d81cde-69bc-4ddf-b370-c45e2c209cfd.png">
<img width="212" alt="image" src="https://user-images.githubusercontent.com/78738614/169063883-433b2fb0-b16f-49b1-93b0-46c46844236b.png">  
 
