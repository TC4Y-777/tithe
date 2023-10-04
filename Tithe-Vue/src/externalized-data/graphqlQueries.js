// Homepage

export const homepageActiveEnityCountQuery = `query activeCount {
    getPersonCount
    getFamilyCount
    getKoottaymaCount
    getParishCount
    getForaneCount
  }`;

export const homepageActiveForaneTableQuery = `query activeForane{
  getAllForanes{
    foraneId
    foraneName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
  }
}`;

export const homepageActiveParishTableQuery = `query activeParish{
  getAllParishes{
    parishId
    parishName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
    forane{
      foraneName
    }
  }
}`;

export const homepageActiveFamilyTableQuery = `query activeFamily{
  getAllFamilies{
    familyId
    familyName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
    koottayma{
      koottaymaName
      parish{
        parishName
      }
    }
  }
}`;

export const homepageActivePersonTableQuery = `query activePerson{
  getAllPersons{
    personId
    personName
    baptismName
    gender
    dob
    relation{
      relationName
    }
    family{
      familyName
    }
  }
}`;

// Forane Page

export const foraneAllForaneListQuery = `query foranePageActiveForane{
  getAllForanes{
    foraneId
    foraneName
    address{
      street{
        streetName
      }
    }
  }
}`;

export const foranePageActiveEnityCountQuery = `query activeCountByForane ($id: ID!) {
    getPersonCountByForane (foraneId: $id)
    getFamilyCountByForane (foraneId: $id)
    getKoottaymaCountByForane (foraneId: $id)
    getParishCountByForane (foraneId: $id)
  }`;

export const foranePageActiveParishTableQuery = `query activeParishByForane ($foraneId: ID!){
  getAllParishesByForane (foraneId: $foraneId){
    parishId
    parishName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
    forane{
      foraneName
    }
  }
}`;

export const foranePageActiveFamilyTableQuery = `query activeFamilyByForane ($foraneId: ID!){
  getAllFamiliesByForane (foraneId: $foraneId){
    familyId
    familyName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
    koottayma{
      koottaymaName
      parish{
        parishName
      }
    }
  }
}`;

export const foranePageActivePersonTableQuery = `query activePersonByForane ($foraneId: ID!){
  getAllPersonsByForane (foraneId: $foraneId) {
    personId
    personName
    baptismName
    gender
    dob
    relation{
      relationName
    }
    family{
      familyName
    }
  }
}`;

// Parish Page

export const parishAllForaneListQuery = foraneAllForaneListQuery;

export const parishAllParishListQuery = `query parishPageActiveParish ($foraneId: ID!){
  getAllParishesByForane (foraneId: $foraneId){
    parishId
    parishName
    address{
      street{
        streetName
      }
    }
  }
}`;

export const parishPageActiveEnityCountQuery = `query activeCountByParish ($id: ID!) {
    getPersonCountByParish (parishId: $id)
    getFamilyCountByParish (parishId: $id)
    getKoottaymaCountByParish (parishId: $id)
  }`;

export const parishPageActiveKoottaymaTableQuery = `query activeKoottaymaByParish ($parishId: ID!){
  getAllKoottaymasByParish (parishId: $parishId){
    koottaymaId
    koottaymaName
  }
}`;

export const parishPageActiveFamilyTableQuery = `query activeFamilyByParish ($parishId: ID!){
  getAllFamiliesByParish (parishId: $parishId){
    familyId
    familyName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
    koottayma{
      koottaymaName
      parish{
        parishName
      }
    }
  }
}`;

export const parishPageActivePersonTableQuery = `query activePersonByParish ($parishId: ID!){
  getAllPersonsByParish (parishId: $parishId) {
    personId
    personName
    baptismName
    gender
    dob
    relation{
      relationName
    }
    family{
      familyName
    }
  }
}`;

// Koottayma Page

export const koottaymaAllForaneListQuery = foraneAllForaneListQuery;

export const koottaymaAllParishListQuery = parishAllParishListQuery;

export const koottaymaAllKoottaymaListQuery = `query koottaymaPageActiveKoottayma ($parishId: ID!){
  getAllKoottaymasByParish (parishId: $parishId){
    koottaymaId
    koottaymaName
  }
}`;

export const koottaymaPageActiveEnityCountQuery = `query activeCountByKoottayma ($id: ID!) {
    getPersonCountByKoottayma (koottaymaId: $id)
    getFamilyCountByKoottayma (koottaymaId: $id)
  }`;

export const koottaymaPageActiveFamilyTableQuery = `query activeFamilyByKoottayma ($koottaymaId: ID!){
  getAllFamiliesByKoottayma (koottaymaId: $koottaymaId){
    familyId
    familyName
    address{
      buildingName
      street{
        streetName
      }
      city{
        cityName
      }
    }
    koottayma{
      koottaymaName
      parish{
        parishName
      }
    }
  }
}`;

export const koottaymaPageActivePersonTableQuery = `query activePersonByKoottayma ($koottaymaId: ID!){
  getAllPersonsByKoottayma (koottaymaId: $koottaymaId) {
    personId
    personName
    baptismName
    gender
    dob
    relation{
      relationName
    }
    family{
      familyName
    }
  }
}`;

// Family Page

export const familyAllForaneListQuery = foraneAllForaneListQuery;

export const familyAllParishListQuery = parishAllParishListQuery;

export const familyAllFamilyListQuery = `query familyPageActiveFamily ($parishId: ID!){
  getAllFamiliesByParish (parishId: $parishId){
    familyId
    familyName
    headOfFamily{
      personId
      personName
      baptismName
      relation{
        relationId
        relationName
      }
    }
    address{
      street{
        streetName
      }
    }
    persons{
      personId
      personName
      baptismName
      relation{
        relationId
        relationName
      }
    }
  }
}`;

export const familyAllKoottaymaListQuery = koottaymaAllKoottaymaListQuery;

export const familyPageActiveEnityCountQuery = `query activeCountByFamily ($id: ID!) {
    getPersonCountByFamily (familyId: $id)
  }`;

export const familyPagePersonsQuery = `query familyMembersQuery ($id: ID!){
  getAllPersonsByFamily (familyId: $id) {
    personId
    personName
    baptismName
    relation{
      relationId
      relationName
    }
  }
}`;

export const familyPageActivePersonTableQuery = `query activePersonByFamily ($familyId: ID!){
  getAllPersonsByFamily (familyId: $familyId) {
    personId
    personName
    baptismName
    gender
    dob
    relation{
      relationName
    }
    family{
      familyName
    }
  }
}`;

// Person Page

export const personAllForaneListQuery = foraneAllForaneListQuery;

export const personAllParishListQuery = parishAllParishListQuery;

export const personAllPersonListQuery = `query personPageActivePerson ($parishId: ID!){
  getAllPersonsByParish (parishId: $parishId){
    personId
    personName
  }
}`;

export const personAllFamilyListQuery = familyAllFamilyListQuery;

export const personAllRelationListQuery = `query personPageRelations{
    getAllRelations{
        relationId
        relationName
    }
}`;

// Address Queries

export const similarStreetListQuery = `query similarStreetList ($streetName: String!){
  getSimilarStreets (streetName: $streetName){
    streetId
    streetName
  }
}`;

export const similarCityListQuery = `query similarCityList ($cityName: String!){
  getSimilarCities (cityName: $cityName){
    cityId
    cityName
  }
}`;

export const similarDistrictListQuery = `query similarDistrictList ($districtName: String!){
  getSimilarDistricts (districtName: $districtName){
    districtId
    districtName
  }
}`;

export const similarStateListQuery = `query similarStateList ($stateName: String!){
  getSimilarStates (stateName: $stateName){
    stateId
    stateName
  }
}`;

export const similarPincodeListQuery = `query similarPincodeList ($pincode: String!){
  getSimilarPincodes (pincode: $pincode){
    pincodeId
    pincode
  }
}`;
