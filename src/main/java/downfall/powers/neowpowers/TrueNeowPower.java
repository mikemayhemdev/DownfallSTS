package downfall.powers.neowpowers;

import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import downfall.downfallMod;
import downfall.powers.EnemyDemonFormPower;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class TrueNeowPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowSpirit");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowIronclad384.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowIronclad332.png"));

    private boolean ironclad;
    private boolean silent;
    private boolean defect;
    private boolean watcher;
    private boolean hermit;

    public TrueNeowPower(final AbstractCreature owner, boolean ic, boolean si, boolean de, boolean wa, boolean he) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.canGoNegative = false;
        this.ironclad = ic;
        this.silent = si;
        this.defect = de;
        this.watcher = wa;
        this.hermit = he;
        this.updateDescription();

    }

    @Override
    public void onSpecificTrigger() {
        if (ironclad){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyDemonFormPower(owner, 1), 1));
        }
        if (silent){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(owner, 2), 2));
        }
        if (defect){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new BufferPower(owner, 2), 2));
        }
        if (watcher){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new NeowMantraPower(owner, 5), 5));
        }
        if (hermit){
            ArrayList<AbstractCard> ac = downfallMod.getRandomDownfallCurse(2);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac.get(0).makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.35F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac.get(1).makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.65F, (float) Settings.HEIGHT / 2.0F));
        }
    }



    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if (ironclad) this.description = this.description + DESCRIPTIONS[1];
        if (silent) this.description = this.description + DESCRIPTIONS[2];
        if (defect) this.description = this.description + DESCRIPTIONS[3];
        if (watcher) this.description = this.description + DESCRIPTIONS[4];
        if (hermit) this.description = this.description + DESCRIPTIONS[5];
    }

}