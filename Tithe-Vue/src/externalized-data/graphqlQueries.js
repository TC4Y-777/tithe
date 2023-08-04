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
