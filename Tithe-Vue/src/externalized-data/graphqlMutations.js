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

// Address Mutations

