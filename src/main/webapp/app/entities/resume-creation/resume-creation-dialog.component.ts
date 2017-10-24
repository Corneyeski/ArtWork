import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ResumeCreation } from './resume-creation.model';
import { ResumeCreationPopupService } from './resume-creation-popup.service';
import { ResumeCreationService } from './resume-creation.service';
import { Tool, ToolService } from '../tool';
import { Language, LanguageService } from '../language';
import { City, CityService } from '../city';
import { Training, TrainingService } from '../training';
import { Experience, ExperienceService } from '../experience';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-resume-creation-dialog',
    templateUrl: './resume-creation-dialog.component.html'
})
export class ResumeCreationDialogComponent implements OnInit {

    resumeCreation: ResumeCreation;
    isSaving: boolean;

    tools: Tool[];

    languages: Language[];

    cities: City[];

    trainings: Training[];

    experiences: Experience[];
    birthdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private resumeCreationService: ResumeCreationService,
        private toolService: ToolService,
        private languageService: LanguageService,
        private cityService: CityService,
        private trainingService: TrainingService,
        private experienceService: ExperienceService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.toolService
            .query({filter: 'resumecreation-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.resumeCreation.tool || !this.resumeCreation.tool.id) {
                    this.tools = res.json;
                } else {
                    this.toolService
                        .find(this.resumeCreation.tool.id)
                        .subscribe((subRes: Tool) => {
                            this.tools = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.languageService.query()
            .subscribe((res: ResponseWrapper) => { this.languages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cityService.query()
            .subscribe((res: ResponseWrapper) => { this.cities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.trainingService.query()
            .subscribe((res: ResponseWrapper) => { this.trainings = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.experienceService.query()
            .subscribe((res: ResponseWrapper) => { this.experiences = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.resumeCreation, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.resumeCreation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.resumeCreationService.update(this.resumeCreation));
        } else {
            this.subscribeToSaveResponse(
                this.resumeCreationService.create(this.resumeCreation));
        }
    }

    private subscribeToSaveResponse(result: Observable<ResumeCreation>) {
        result.subscribe((res: ResumeCreation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ResumeCreation) {
        this.eventManager.broadcast({ name: 'resumeCreationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackToolById(index: number, item: Tool) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }

    trackCityById(index: number, item: City) {
        return item.id;
    }

    trackTrainingById(index: number, item: Training) {
        return item.id;
    }

    trackExperienceById(index: number, item: Experience) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-resume-creation-popup',
    template: ''
})
export class ResumeCreationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resumeCreationPopupService: ResumeCreationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.resumeCreationPopupService
                    .open(ResumeCreationDialogComponent as Component, params['id']);
            } else {
                this.resumeCreationPopupService
                    .open(ResumeCreationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
