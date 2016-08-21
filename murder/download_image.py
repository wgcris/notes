import urllib
import json

#  urllib.urlretrieve("http://google.com/index.html", filename="manifest.json")
#  urllib.urlretrieve("http://google",filename="image.json")

f=open("image.json")
str_image = f.read(-1)
f.close()

json_reader = json.loads(str_image)

image_list = []


for a in json_reader.keys():
    if a == "rootfs":
        for b in json_reader[a].keys():
            if b == "diff_ids":
                print json_reader[a][b]
                image_list.append(json_reader[a][b])


print image_list