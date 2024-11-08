package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.EvasiveProtocolPower;
import sneckomod.SneckoMod;

import static guardian.GuardianMod.makeBetaCardPath;

public class EvasiveProtocol extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("EvasiveProtocol");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slickScales.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    private static final int DEX = 2;
    private static final int BRACE_PER_TURN = 3;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public EvasiveProtocol() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = DEX;
        this.secondaryM = BRACE_PER_TURN;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("EvasiveProtocol.png"));
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EvasiveProtocolPower(p, magicNumber)));
//        if (p.stance instanceof DefensiveMode) {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
//        }
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BracePerTurnPower(p, this.secondaryM)));
    }

    public AbstractCard makeCopy() {
        return new EvasiveProtocol();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            upgradeMagicNumber(2);
            upgradeBaseCost(0);
        }
    }

    public void updateDescription() {
//        if (this.socketCount > 0) {
//            if (upgraded && UPGRADED_DESCRIPTION != null) {
//                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
//            } else {
//                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
//            }
//        }
        this.initializeDescription();
    }
}


