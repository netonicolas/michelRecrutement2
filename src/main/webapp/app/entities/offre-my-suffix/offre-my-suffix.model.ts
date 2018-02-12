import { BaseEntity } from './../../shared';

export const enum TypeOffre {
    'CDI',
    'CDD',
    'STAGE'
}

export class OffreMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public nomOffre?: string,
        public descriptionOffre?: string,
        public salaireMin?: number,
        public salaireMax?: number,
        public typeOffre?: TypeOffre,
        public dateOffres?: any,
        public entrepriseId?: number,
        public categorieOffreId?: number,
        public competences?: BaseEntity[],
    ) {
    }
}
