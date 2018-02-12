import { BaseEntity } from './../../shared';

export class LieuMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public pays?: string,
        public ville?: string,
        public departement?: string,
        public zipCode?: string,
        public rue?: string,
        public numero?: number,
    ) {
    }
}
