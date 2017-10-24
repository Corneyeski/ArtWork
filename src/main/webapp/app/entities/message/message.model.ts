import { BaseEntity, User } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public attachedContentType?: string,
        public attached?: any,
        public time?: any,
        public user?: User,
        public conversation?: BaseEntity,
    ) {
    }
}
