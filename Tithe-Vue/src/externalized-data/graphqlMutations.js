// Address Mutations

export const createForaneMutation = `mutation createForane ($forane: ForaneMutationInput!){
    createOneForane (forane: $forane){
        foraneId
        foraneName
    }
}
`;
