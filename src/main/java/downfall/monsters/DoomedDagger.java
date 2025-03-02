package downfall.monsters;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.cardmods.RetainCardMod;
import hermit.cards.ImpendingDoom;

public class DoomedDagger extends AbstractMonster{
    public static final String ID = downfallMod.makeID("DoomedDagger");
    public static final String NAME = CardCrawlGame.languagePack.getMonsterStrings( downfallMod.makeID("DoomedDagger")).NAME;

    public boolean firstMove = true;

    public DoomedDagger(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(50, 60), 0.0F, -50.0F, 140.0F, 130.0F, null, x, y);
        this.initializeAnimation();
        this.damage.add(new DamageInfo(this, 9));
        this.damage.add(new DamageInfo(this, 25));
    }

    public void initializeAnimation() {
        this.loadAnimation("images/monsters/theForest/mage_dagger/skeleton.atlas", "images/monsters/theForest/mage_dagger/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractCard q = new ImpendingDoom();
                //CardModifierManager.addModifier(q, new RetainCardMod());
                addToBot(new MakeTempCardInDrawPileAction((q), 1, false, true));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUICIDE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this, this, this.currentHealth));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hurt", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            this.stateData.setMix("Hurt", "Idle", 0.1F);
            this.stateData.setMix("Idle", "Hurt", 0.1F);
        }

    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, 9);
        } else {
            this.setMove((byte)2, AbstractMonster.Intent.ATTACK, 25);
        }
    }

    public void changeState(String key) {
        byte var3 = -1;
        switch(key.hashCode()) {
            case -1143642610:
                if (key.equals("SUICIDE")) {
                    var3 = 1;
                }
                break;
            case 1941037640:
                if (key.equals("ATTACK")) {
                    var3 = 0;
                }
        }

        switch(var3) {
            case 0:
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case 1:
                this.state.setAnimation(0, "Attack2", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

}