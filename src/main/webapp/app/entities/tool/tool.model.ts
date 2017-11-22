import { BaseEntity } from './../../shared';

export const enum ToolType {
    'BUTTON',
    'MODAL'
}

export class Tool implements BaseEntity {
    constructor(
        public id?: number,
        public active?: boolean,
        public time?: any,
        public name?: string,
        public description?: string,
        public type?: ToolType,
        public userExts?: BaseEntity[],
    ) {
        this.active = false;
    }
}
