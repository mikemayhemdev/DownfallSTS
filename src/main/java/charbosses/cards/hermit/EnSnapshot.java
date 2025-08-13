package charbosses.cards.hermit;

import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import hermit.actions.SnapshotAction;
import hermit.cards.Headshot;
import hermit.cards.Snapshot;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnSnapshot extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Snapshot";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Snapshot.ID);

    public EnSnapshot() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/card_snapshot.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.BASIC, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEFEND);
        this.baseDamage = 7;
        tags.add(downfallMod.CHARBOSS_DEADON);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        AbstractPower concentration = this.owner.getPower(HermitConcentrationPower.POWER_ID);
        if (concentration != null && concentration.amount > 0) {

            this.addToBot(new SnapshotAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn),1));

        }
        else {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn),
                            EnumPatch.HERMIT_GUN));
        }
    }

    @Override
    public void onSpecificTrigger() {
        this.intentActive = false;
        this.createIntent();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSnapshot();
    }
}
