import { BaseEntity, User } from './../../shared';

export class Offer implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: any,
        public time?: any,
        public salary?: number,
        public duration?: number,
        public status?: boolean,
        public tags?: string,
        public location?: string,
        public contract?: string,
        public profession?: BaseEntity,
        public creator?: User,
        public userExts?: BaseEntity[],
    ) {
        this.status = false;
    }
}
