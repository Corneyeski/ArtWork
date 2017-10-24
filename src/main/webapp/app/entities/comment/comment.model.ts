import { BaseEntity, User } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public comment?: string,
        public time?: any,
        public likes?: number,
        public multimedia?: BaseEntity,
        public response?: BaseEntity,
        public user?: User,
    ) {
    }
}
