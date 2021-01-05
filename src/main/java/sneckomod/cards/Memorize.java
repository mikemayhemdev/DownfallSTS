package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MemorizeAction;
import sneckomod.actions.NopeAction;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.patches.UnknownExtraUiPatch;

public class Memorize extends AbstractSneckoCard {

    public final static String ID = makeID("Memorize");

    //stupid intellij stuff SKILL, SELF, COMMON

    public Memorize() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        selfRetain = true;
        FleetingField.fleeting.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MemorizeAction());
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.update();
            if (UnknownExtraUiPatch.parentCard.get(c) != null){
                return super.canUse(p, m);
            }
        }


        return false;
    }
}