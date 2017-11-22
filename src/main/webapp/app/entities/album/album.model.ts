import { BaseEntity } from './../../shared';

export class Album implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public time?: any,
        public multimedias?: BaseEntity[],
    ) {
    }
}
