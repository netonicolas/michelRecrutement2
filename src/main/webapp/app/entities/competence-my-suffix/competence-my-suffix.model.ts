import { BaseEntity } from './../../shared';

export class CompetenceMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public nomCompetence?: string,
        public competences?: BaseEntity[],
    ) {
    }
}
