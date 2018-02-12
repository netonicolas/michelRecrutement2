import { BaseEntity } from './../../shared';

export class CategorieOffreMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public nomCategorie?: string,
    ) {
    }
}
