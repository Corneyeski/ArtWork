/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TrainingDetailComponent } from '../../../../../../main/webapp/app/entities/training/training-detail.component';
import { TrainingService } from '../../../../../../main/webapp/app/entities/training/training.service';
import { Training } from '../../../../../../main/webapp/app/entities/training/training.model';

describe('Component Tests', () => {

    describe('Training Management Detail Component', () => {
        let comp: TrainingDetailComponent;
        let fixture: ComponentFixture<TrainingDetailComponent>;
        let service: TrainingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [TrainingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TrainingService,
                    JhiEventManager
                ]
            }).overrideTemplate(TrainingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrainingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Training(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.training).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
