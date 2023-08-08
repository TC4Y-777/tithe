import {
  mdiAccountCircle,
  mdiMonitor,
  mdiChurch,
  mdiChurchOutline,
  mdiHandsPray,
  mdiAccountMultiple,
  mdiAccount,
  mdiFamilyTree,
  mdiSchool,
  mdiBriefcase,
} from "@mdi/js";

export default [
  {
    to: "/dashboard",
    icon: mdiMonitor,
    label: "Dashboard",
  },
  {
    to: "/forane",
    icon: mdiChurch,
    label: "Forane",
  },
  {
    to: "/parish",
    icon: mdiChurchOutline,
    label: "Parish",
  },
  {
    to: "/koottayma",
    icon: mdiHandsPray,
    label: "Koottayma",
  },
  {
    to: "/family",
    icon: mdiAccountMultiple,
    label: "Family",
    menu: [
      {
        to: "/relation",
        icon: mdiFamilyTree,
        label: "Relation",
      },
    ],
  },
  {
    to: "/person",
    icon: mdiAccount,
    label: "Person",
    menu: [
      {
        to: "/education",
        icon: mdiSchool,
        label: "Education",
      },
      {
        to: "/occupation",
        icon: mdiBriefcase,
        label: "Occupation",
      },
    ],
  },
  {
    to: "/profile",
    label: "Profile",
    icon: mdiAccountCircle,
  },
];
