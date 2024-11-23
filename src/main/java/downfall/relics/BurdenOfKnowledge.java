package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.actions.WaitForEscapeAction;
import downfall.downfallMod;
import gremlin.characters.GremlinCharacter;

public class BurdenOfKnowledge extends CustomRelic {

    public static final String ID = downfallMod.makeID("BurdenOfKnowledge");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/Burden.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/Burden.png"));

    public BurdenOfKnowledge() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.decreaseMaxHealth(10);
    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (!c.upgraded) {
            c.upgrade();
        }
    }

    public void atBattleStart() {

        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MasterRealityPower(AbstractDungeon.player)));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -2), -2));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -2), -2));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

    }

    public AbstractRelic makeCopy() {
        return new BurdenOfKnowledge();
    }
}
