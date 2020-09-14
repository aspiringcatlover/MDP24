print("hello")
people_num = int(input("enter"))
print("fk this")
height_list = list(input().replace(" ",""))
count = 0
if people_num>0:
    count += 1


for i in range(0, people_num):
    if i+1 != people_num: #not referencing out of the list
        if height_list[i] > height_list[i+1]:
            count += 1

print(count)
