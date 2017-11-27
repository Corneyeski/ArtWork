import { BaseEntity } from './../../shared';

export class Profession implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public sector?: string,
        public workersNum?: number,
    ) {
    }
}
