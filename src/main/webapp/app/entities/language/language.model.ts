import { BaseEntity } from './../../shared';

export class Language implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
    ) {
    }
}
