import { BaseEntity, User } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public time?: any,
        public likes?: number,
        public multimedia?: BaseEntity,
        public comment?: BaseEntity,
        public user?: User,
    ) {
    }
}
