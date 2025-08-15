package downfall.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.expansionContentMod;

import java.util.Arrays;

import static expansioncontent.cards.AbstractExpansionCard.makeID;

@Deprecated
@NoCompendium
@NoPools

public class BloodySacrifice extends AbstractDownfallCard {


    public static final String ID = makeID("BloodySacrifice");
    public static final String IMG_PATH = expansionContentMod.makeCardPath("BloodySacrifice.png");
    private static final CardStrings cardStrings;
    private int lose_hp;

    public BloodySacrifice() {
        super(ID, cardStrings.NAME, IMG_PATH, 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        isEthereal = true;
        baseMagicNumber = magicNumber = 8;
        exhaust = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        lose_hp = MathUtils.floor((float) AbstractDungeon.player.maxHealth * (float)magicNumber * 0.01f);
        rawDescription = (cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0].replace("{amount}",Integer.toString(lose_hp)) );
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.maxHealth -= this.lose_hp;
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(p, 1, true), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
