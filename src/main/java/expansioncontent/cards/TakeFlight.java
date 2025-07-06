package expansioncontent.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.RisingChantPower;
import awakenedOne.relics.CursedBlessing;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import expansioncontent.expansionContentMod;

import static awakenedOne.AwakenedOneMod.ACTIVECHANT;
import static awakenedOne.AwakenedOneMod.CHANT;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class TakeFlight extends AbstractExpansionCard {
    public static final String ID = makeID("TakeFlight");

    private static final int BLOCK = 14;

    private static final int UPGRADE_BLOCK = 5;

    private static final int MAGIC = 1;

    public TakeFlight() {
        super(ID, 1, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(expansionContentMod.STUDY);
        this.tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_awakenedone.png", "expansioncontentResources/images/1024/bg_boss_awakenedone.png");
        this.baseMagicNumber = this.magicNumber = 6;
        this.baseDownfallMagic = this.downfallMagic = 8;
        tags.add(CardTags.HEALING);
        this.tags.add(AwakenedOneMod.CHANT);
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "TakeFlight.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if (Wiz.isChantActive()) {
                chant();
            }

            if (!Wiz.isChantActive()) {
                if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                    chant();
                    awaken(1);
                } else {
                    atb(new HealAction(p, p, magicNumber));
                }
            }
        }

        if (!AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if (Wiz.isChantActive()) {
                chant();
            }
            if (!Wiz.isChantActive()) {
                atb(new HealAction(p, p, magicNumber));
            }
        }
    }

    public void chant() {
        atb(new HealAction(AbstractDungeon.player, AbstractDungeon.player, downfallMagic));

        if (!this.hasTag(ACTIVECHANT) && this.hasTag(CHANT)) {
            this.tags.add(ACTIVECHANT);
            if (AbstractDungeon.player.hasPower(RisingChantPower.POWER_ID)) {
                applyToSelf(new StrengthPower(AbstractDungeon.player, AbstractDungeon.player.getPower(RisingChantPower.POWER_ID).amount));
                AbstractDungeon.player.getPower(RisingChantPower.POWER_ID).onSpecificTrigger();
            }

            if (AbstractDungeon.player.hasRelic(CursedBlessing.ID)) {
                AbstractDungeon.player.getRelic(CursedBlessing.ID).onTrigger();
            }
        }
        if (AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            AbstractDungeon.player.getRelic(KTRibbon.ID).onTrigger();
        }

    }

    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type == CardType.POWER) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            upgradeDownfall(4);
        }
    }
}
