import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public totalUsers?: number,
        public city?: BaseEntity,
    ) {
    }
}
