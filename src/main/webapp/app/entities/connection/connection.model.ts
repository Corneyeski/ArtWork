import { BaseEntity, User } from './../../shared';

export class Connection implements BaseEntity {
    constructor(
        public id?: number,
        public acepted?: boolean,
        public title?: string,
        public message?: string,
        public time?: any,
        public sender?: User,
        public receiver?: User,
    ) {
        this.acepted = false;
    }
}
