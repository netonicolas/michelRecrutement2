import { BaseEntity } from './../../shared';

export class EntrepriseMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public entrepriseName?: string,
        public telephoneEntreprise?: string,
        public siren?: number,
        public lieuEntrepriseId?: number,
    ) {
    }
}
