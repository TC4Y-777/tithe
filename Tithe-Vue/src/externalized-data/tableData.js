export const homepageTableTabTitle = [
  { title: "Forane", icon: "mdiChurch" },
  { title: "Parish", icon: "mdiChurchOutline" },
  { title: "Family", icon: "mdiAccountMultiple" },
  { title: "Person", icon: "mdiAccount" },
];

export const foranePageTableTabTitle = [
  { title: "Parish", icon: "mdiChurchOutline" },
  { title: "Family", icon: "mdiAccountMultiple" },
  { title: "Person", icon: "mdiAccount" },
];

export const foraneTableHeaders = [
  { name: "Name", dataKey: "foraneName" },
  { name: "Building", dataKey: "address.buildingName" },
  { name: "Street", dataKey: "address.street.streetName" },
  { name: "City", dataKey: "address.city.cityName" },
];

export const parishTableHeaders = [
  { name: "Name", dataKey: "parishName" },
  { name: "Building", dataKey: "address.buildingName" },
  { name: "Street", dataKey: "address.street.streetName" },
  { name: "City", dataKey: "address.city.cityName" },
  { name: "Forane", dataKey: "forane.foraneName" },
];

export const familyTableHeaders = [
  { name: "Name", dataKey: "familyName" },
  { name: "Building", dataKey: "address.buildingName" },
  { name: "Street", dataKey: "address.street.streetName" },
  { name: "City", dataKey: "address.city.cityName" },
  { name: "Koottayma", dataKey: "koottayma.koottaymaName" },
  { name: "Parish", dataKey: "koottayma.parish.parishName" },
];

export const personTableHeaders = [
  { name: "Name", dataKey: "personName" },
  { name: "Baptism Name", dataKey: "baptismName" },
  { name: "Gender", dataKey: "gender" },
  { name: "Date of Birth", dataKey: "dob" },
  { name: "Relation", dataKey: "relation.relationName" },
  { name: "Family", dataKey: "family.familyName" },
];
