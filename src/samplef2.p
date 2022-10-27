import cv2
import os
import numpy as np
from os import listdir
from os.path import isfile, join

file = open('C:\\SafeApp/path.txt')
all_lines = file.readlines()

s=s=all_lines[2][5:6]+all_lines[2][7:-1]
s1='src/sample/savedimg/'
#data_path = 'C:/Users/Rajan/face1/src/sample/savedimg/'
data_path=os.path.join(s,s1)
onlyfiles = [f for f in listdir(data_path) if isfile(join(data_path,f))]

Training_Data, Labels = [], []

for i, files in enumerate(onlyfiles):
    image_path = data_path + onlyfiles[i]
    images = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    Training_Data.append(np.asarray(images, dtype=np.uint8))
    Labels.append(i)

Labels = np.asarray(Labels, dtype=np.int32)
#print("1")
#print(dir (cv2.face)) 
model = cv2.face.LBPHFaceRecognizer_create()
#print("2")

model.train(np.asarray(Training_Data), np.asarray(Labels))
#print("3")

#print("Model Training Complete!!!!!")


face_classifier = cv2.CascadeClassifier(s+
    '/src/sample/data/haarcascades/haarcascade_frontalface_default.xml')



def face_detector(img,size=0.5):
    gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    faces = face_classifier.detectMultiScale(gray,1.3,5)

    if faces is ():
        return img,[]
    for(x,y,w,h) in faces:
        cv2.rectangle(img,(x,y),(x+w,y+h),(0,255,255),2)
        roi = img[y:y+h,x:x+w]
        roi= cv2.resize(roi,(200,200))

    return img,roi
cap=cv2.VideoCapture(0)
cnt=0
cnt1=0
cnt2=0
while True:
    ret,frame = cap.read()
    image,face = face_detector(frame)
    try:
        face = cv2.cvtColor(face,cv2.COLOR_BGR2GRAY)
        result = model.predict(face)
        if result[1]<500:
            confidence = int(100*(1-(result[1])/300))
            display_string = str(confidence)+'% confidence it is user'
        cv2.putText(image,display_string,(100,120),cv2.FONT_HERSHEY_COMPLEX,1,(250,120,255),2)

        if confidence>=89:
            cv2.putText(image,"unlocked",(250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (0,255,0), 2)
            cv2.imshow('Face Cropper',image)
            cnt=cnt+1

        else:
            cv2.putText(image, "Locked", (250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (0,0,255), 2)
            cv2.imshow('Face Cropper', image)
            cnt2=cnt2+1

    except:
        cv2.putText(image,"Face Not Found",(250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (255,0,0), 2)
        cv2.imshow('Face Cropper',image)
        cnt1=cnt1+1
        pass
    #print(cv2.waitKey(1))
    if cv2.waitKey(1)==27 or cnt==30 or cnt1==150 or cnt2==800:
        cap.release()
        break
    
#cap.release()
cv2.destroyAllWindows()
f=""
def fun1():
    if cnt==30:
        f="1"
        fi=open(s+"/src/sample/test.txt","w+")
        fi.write(f)
        fi.close()
        fi=open(s+"/src/sample/test.txt","r")
        f1=fi.read()
        fi.close()
    else:
        f="0"
        fi=open(s+"/src/sample/test.txt","w+")
        fi.write(f)
        fi.close()
        fi=open(s+"/src/sample/test.txt","r")
        f1=fi.read()
        fi.close()
fun1()