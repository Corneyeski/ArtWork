/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReasonReportDetailComponent } from '../../../../../../main/webapp/app/entities/reason-report/reason-report-detail.component';
import { ReasonReportService } from '../../../../../../main/webapp/app/entities/reason-report/reason-report.service';
import { ReasonReport } from '../../../../../../main/webapp/app/entities/reason-report/reason-report.model';

describe('Component Tests', () => {

    describe('ReasonReport Management Detail Component', () => {
        let comp: ReasonReportDetailComponent;
        let fixture: ComponentFixture<ReasonReportDetailComponent>;
        let service: ReasonReportService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [ReasonReportDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReasonReportService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReasonReportDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReasonReportDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReasonReportService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReasonReport(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reasonReport).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
