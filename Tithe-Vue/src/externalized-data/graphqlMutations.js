// Forane Page

export const createForaneMutation = `mutation createForane ($forane: ForaneMutationInput!){
    createOneForane (forane: $forane){
        foraneId
        foraneName
    }
}
`;

export const deactivateForaneMutation = `mutation removeForane ($foraneId: ID!){
    deactivateOneForane (foraneId: $foraneId){
        foraneId
        foraneName
    }
}
`;

export const activateForaneMutation = `mutation activateForane ($foraneId: ID!){
    activateOneForane (foraneId: $foraneId){
        foraneId
        foraneName
    }
}
`;

// Parish Page

export const createParishMutation = `mutation createParish ($parish: ParishMutationInput!){
    createOneParish (parish: $parish){
        parishId
        parishName
    }
}
`;

export const deactivateParishMutation = `mutation removeParish ($parishId: ID!){
    deactivateOneParish (parishId: $parishId){
        parishId
        parishName
    }
}
`;

export const activateParishMutation = `mutation activateParish ($parishId: ID!){
    activateOneParish (parishId: $parishId){
        parishId
        parishName
    }
}
`;

// Koottayma Page

export const createKoottaymaMutation = `mutation createKoottayma ($koottayma: KoottaymaMutationInput!){
    createOneKoottayma (koottayma: $koottayma){
        koottaymaId
        koottaymaName
    }
}
`;

export const deactivateKoottaymaMutation = `mutation removeKoottayma ($koottaymaId: ID!){
    deactivateOneKoottayma (koottaymaId: $koottaymaId){
        koottaymaId
        koottaymaName
    }
}
`;

export const activateKoottaymaMutation = `mutation activateKoottayma ($koottaymaId: ID!){
    activateOneKoottayma (koottaymaId: $koottaymaId){
        koottaymaId
        koottaymaName
    }
}
`;

// Family Mutations

export const createFamilyMutation = `mutation createFamily ($family: FamilyMutationInput!){
    createOneFamily (family: $family){
        familyId
        familyName
    }
}
`;

export const createFamilyMembers = `mutation createFamilyMembers ($familyId: ID!, $persons: [PersonMutationThroughFamilyInput]!){
    createManyPersonsInOneFamily (familyId: $familyId, persons: $persons){
        personId
        personName
    }
}
`;

export const changeHeadOfFamily = `mutation changeHeadOfFamily ($familyId: ID!, $newHeadOfFamily: PersonRelationSchema!, $persons: [PersonRelationSchema]){
    changeHeadOfFamily (familyId: $familyId, newHeadOfFamily: $newHeadOfFamily, persons: $persons){
        familyId
        familyName
    }
}
`;

export const deactivateFamilyMutation = `mutation removeFamily ($familyId: ID!){
    deactivateOneFamily (familyId: $familyId){
        familyId
        familyName
    }
}
`;

export const activateFamilyMutation = `mutation activateFamily ($familyId: ID!){
    activateOneFamily (familyId: $familyId){
        familyId
        familyName
    }
}
`;

// Person Mutations

export const createPersonMutation = `mutation createPerson ($person: PersonMutationInput!){
    createOnePerson (person: $person){
        personId
        personName
    }
}
`;

export const deactivatePersonMutation = `mutation removePerson ($personId: ID!){
    deactivateOnePerson (personId: $personId){
        personId
        personName
    }
}
`;

export const activatePersonMutation = `mutation activatePerson ($personId: ID!){
    activateOnePerson (personId: $personId){
        personId
        personName
    }
}
`;

export const createRelationMutation = `mutation createRelation ($relation: String!){
    createOneRelation(relation: $relation){
        relationId
        relationName
    }
}`;

export const createOneEducationMutation = `mutation createOneEducation ($education: String){
    createOneEducation(education: $education){
        educationId
        educationName
    }
}`;

export const createOneOccupationMutation = `mutation createOneOccupation ($occupation: String){
    createOneOccupation(occupation: $occupation){
        occupationId
        occupationName
    }
}`;

// Address Mutations

export const createStreetMutation = `mutation createNewStreet ($streetName: String!){
  createOneStreet(streetName: $streetName){
    streetId
    streetName
  }
}`;

export const createCityMutation = `mutation createNewCity ($cityName: String!){
  createOneCity(cityName: $cityName){
    cityId
    cityName
  }
}`;

export const createDistrictMutation = `mutation createNewDistrict ($districtName: String!){
  createOneDistrict(districtName: $districtName){
    districtId
    districtName
  }
}`;

export const createStateMutation = `mutation createNewState ($stateName: String!){
  createOneState(stateName: $stateName){
    stateId
    stateName
  }
}`;

export const createPincodeMutation = `mutation createNewPincode ($pincode: String!){
  createOnePincode(pincode: $pincode){
    pincodeId
    pincode
  }
}`;
