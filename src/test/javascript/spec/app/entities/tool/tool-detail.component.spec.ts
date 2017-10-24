/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ToolDetailComponent } from '../../../../../../main/webapp/app/entities/tool/tool-detail.component';
import { ToolService } from '../../../../../../main/webapp/app/entities/tool/tool.service';
import { Tool } from '../../../../../../main/webapp/app/entities/tool/tool.model';

describe('Component Tests', () => {

    describe('Tool Management Detail Component', () => {
        let comp: ToolDetailComponent;
        let fixture: ComponentFixture<ToolDetailComponent>;
        let service: ToolService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [ToolDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ToolService,
                    JhiEventManager
                ]
            }).overrideTemplate(ToolDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ToolDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ToolService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Tool(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tool).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
