#!/bin/bash

# Creating Patient test file
echo "name: Sabah
surname: Swift
ssID: 5657183210192
adress: 32 rue Michel Ange
city: Le Havre" >>Patient.yml

# Creating HP test file

echo "name: Farah
surname: Ryan
rpps: 42766270552
speciality: GENERALISTE" >>HP.yml

# Creating Prescription test file

echo "content: Sancturelin
quantite: 200g
idHealthProfessional: 60849182633
idPatient: 3921322516464" >>Prescription.yml

echo "Done."
