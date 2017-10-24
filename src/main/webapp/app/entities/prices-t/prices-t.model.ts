import { BaseEntity } from './../../shared';

export class PricesT implements BaseEntity {
    constructor(
        public id?: number,
        public phone?: number,
        public name?: string,
        public city?: string,
        public tool?: BaseEntity,
    ) {
    }
}
