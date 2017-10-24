import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Tool } from './tool.model';
import { ToolService } from './tool.service';

@Component({
    selector: 'jhi-tool-detail',
    templateUrl: './tool-detail.component.html'
})
export class ToolDetailComponent implements OnInit, OnDestroy {

    tool: Tool;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private toolService: ToolService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTools();
    }

    load(id) {
        this.toolService.find(id).subscribe((tool) => {
            this.tool = tool;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTools() {
        this.eventSubscriber = this.eventManager.subscribe(
            'toolListModification',
            (response) => this.load(this.tool.id)
        );
    }
}
