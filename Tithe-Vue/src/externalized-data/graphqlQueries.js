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
  }
}`;

export const foranePageActiveEnityCountQuery = `query activeCountByForane ($id: ID!) {
    getPersonCountByForane (foraneId: $id)
    getFamilyCountByForane (foraneId: $id)
    getKoottaymaCountByForane (foraneId: $id)
    getParishCountByForane (foraneId: $id)
  }`;

export const foranePageActiveParishTableQuery = `query activeParishByForane ($foraneId: ID!){
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

export const foranePageActiveFamilyTableQuery = homepageActiveFamilyTableQuery;

export const foranePageActivePersonTableQuery = homepageActivePersonTableQuery;

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
