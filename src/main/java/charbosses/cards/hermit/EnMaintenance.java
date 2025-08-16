package charbosses.cards.hermit;

import charbosses.bosses.Hermit.Simpler.ArchetypeAct3BasicsSimple;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import hermit.characters.hermit;
import hermit.powers.MaintenanceStrikePower;

public class EnMaintenance extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Maintenance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("downfall:BossMaintenance");

    private int dexterity;

    public EnMaintenance() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/maintenance.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 3;
        dexterity = 1;
        baseBlock = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new MaintenanceStrikePower(m, magicNumber)));
        addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, dexterity)));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBlock(1);
            dexterity += 1;
        }
    }

    @Override
    protected void applyPowersToBlock() {
        this.block = dexterity;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMaintenance();
    }
}
