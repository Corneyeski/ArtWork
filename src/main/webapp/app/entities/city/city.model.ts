import { BaseEntity } from './../../shared';

export class City implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public totalUsers?: number,
    ) {
    }
}
