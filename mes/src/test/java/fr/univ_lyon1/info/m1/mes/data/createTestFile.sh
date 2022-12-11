#!/bin/bash

mkdir Patient HP Prescription

# Creating Patient test file
echo "name: Sabah
surname: Swift
ssID: 5657183210192
adress: 32 rue Michel Ange
city: Le Havre" >> Patient/Patient.yml

# Creating HP test file

echo "name: Farah
surname: Ryan
rpps: 42766270552
speciality: GENERALISTE" >> HP/HP.yml

# Creating Prescription test file

echo "content: Doliprane
quantite: 500g
idHealthProfessional: 36474793744
idPatient: 2018504330565
idPrescription: 82505548-52be-4807-83f6-a453eaf24b78" >> Prescription/Prescription.yml

echo "createTestFile Script Finished."
