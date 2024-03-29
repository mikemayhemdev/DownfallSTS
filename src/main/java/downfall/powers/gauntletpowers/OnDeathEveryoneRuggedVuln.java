package downfall.powers.gauntletpowers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import hermit.powers.Rugged;

public class OnDeathEveryoneRuggedVuln extends AbstractPower {
    public static final String POWER_ID = downfallMod.makeID("OnDeathEveryoneRuggedVuln");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowRez84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowRez32.png"));


    public OnDeathEveryoneRuggedVuln(final AbstractCreature owner, final int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        loadRegion("curiosity");

        this.name = NAME;

        this.updateDescription();

        this.canGoNegative = false;
    }

    @Override
    public void onDeath() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this.owner, new Rugged(AbstractDungeon.player, amount), amount));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this.owner, new VulnerablePower(AbstractDungeon.player, amount, true), amount));
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDying && !m.isDead) {
                addToBot(new ApplyPowerAction(m, this.owner, new Rugged(m, amount), amount));
                addToBot(new ApplyPowerAction(m, this.owner, new VulnerablePower(AbstractDungeon.player, amount, true), amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

}